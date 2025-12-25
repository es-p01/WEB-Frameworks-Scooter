import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance; // the singleton instance that has been registered
    session;
    router;
    unregister; // callback function to unregister this instance at shutdown

    constructor(session, router) {

        this.session = session;
        this.router = router;
        //fetchIntercept does not register the object closure, only the methods as functions
        // so we need an additional static reference to the singleton's attributes
        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this);
        console.log("FetchInterceptor has been registered; current token = ",
            FetchInterceptor.theInstance.session.currentToken);
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.session.currentToken;

        if (token == null) {
            // no change
            return [url, options];
        } else if (options == null) {
            // the authorisation header is the only custom option
            return [url, {headers: {Authorization: token}}]
        } else {
            // add authorization header to other options
            let newOptions = {...options};
            newOptions.headers = {
                //combine new Authorization header with existing headers
                ...(newOptions.headers || {}),
                Authorization: token
            }
            console.log("FetchInterceptor request: ", url, newOptions);
            return [url, newOptions];
        }
    }
    response(response) {
        // console.log("FetchInterceptor response: ", response);
        if (response.status >= 400 && response.status < 600) {
            FetchInterceptor.theInstance.handleErrorInResponse(response);
        }
        return response;
    }

    requestError(error) {
        // Called when an error occured during another 'request' interceptor call
        console.log("FetchInterceptor requestError: ", error);
        return Promise.reject(error);
    }
    responseError(error) {
        // Handle a fetch error
        console.log("FetchInterceptor responseError: ", error);
        return Promise.reject(error);
    }



    async handleErrorInResponse(response){
        if (response.status === 401) {
            // unauthorised request, redirect to signIn page
            alert("You are unauthorized please login again")
            this.router.push({ path: '/sign-out',});
        }
    }



}
