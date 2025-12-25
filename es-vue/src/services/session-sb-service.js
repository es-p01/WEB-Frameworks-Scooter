export class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCES_URL;
    _currentToken;
    _currentAccount;


    constructor(resourcesUrl, browserStorageItemName) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;
        this._currentToken = null;
        this.getTokenFromBrowserStorage();
    }

    get currentToken() {
        return this._currentToken;
    }

    get currentAccount() {
        return this._currentAccount;
    }

    isAuthenticated() {
        return this._currentAccount != null;
    }

    // logs on to the backend, and retrieves user details and the JWT authentication token from the backend.
    async asyncSignIn(email, password) /* :Promise<User> */ {
        const body = JSON.stringify({email: email, password: password});
        let response = await fetch(this.RESOURCES_URL + "/login",
            {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: body,
                credentials: 'include',
            })
        if (response.ok) {
            let user = await response.json();
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
                user);
            return user;
        } else {
            console.log(response)
            return null;
        }
    }

    //discards user details and the JWT authentication token from the service.
    signOut() {
        this.saveTokenIntoBrowserStorage(null, null);
    }

    // saves the JWT authentication token and user details into the service and browser storage
    // for automatic retrieval after application reload
    saveTokenIntoBrowserStorage(token, user) {
        this._currentToken = token;
        this._currentAccount = user;
        // allow for different user sessions from the same computer
        // sessionStorage keeps different items per browser tab
        // localStorage keeps a single item per browser vendor
        // both isolate the items per server domain of the page (including port number?)
        if (token == null) {
            this._currentAccount = null;
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");

            //remove the token+account from local storage
            window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");

        } else {
            console.log("New token for " + user.name + ": " + token);
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC", JSON.stringify(user));
            // save the new token+account in localStorage
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC", JSON.stringify(user));

        }
    }


    //retrieves the JTW authentication token and user details from the browser storage into
    // the service after application reload.
    getTokenFromBrowserStorage() {
        if (this._currentToken != null) return this._currentToken
        this._currentToken = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        let jsonAccount = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");

        if (this._currentToken == null) {
            // find the token+account in local storage and replicate to this session if found
            const localToken = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
            const localAcc = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");

            if (localToken != null && localAcc != null){
                this._currentToken = localToken;
                jsonAccount = localAcc;
                window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME,localToken);
                window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC", localAcc)
            }
                }
        if (jsonAccount != null) {
            this._currentAccount = JSON.parse(jsonAccount);
        }
        //console.log("SessionService recovered token: ", this._currentToken);
        //console.log("Current Account:", this._currentAccount);
        return this._currentToken;

    }
}
