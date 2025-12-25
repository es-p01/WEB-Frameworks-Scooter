import {Scooter} from "@/models/scooter.js";

export class ScootersAdaptor /* implements RESTAdaptor<Scooter> */ {
    resourcesUrl;   // the URL of the scooters resource endpoint
    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created ScootersAdaptor for " + resourcesUrl);
    }

    // issues an asynchronous AJAX fetch request with JSON content
    // request methods PUT, POST and DELETE and request headers can be passed via options
    async fetchJson(url, options = null) {
        let response = await fetch(url, options)
        if (response.ok) {
            return await response.json();
        } else {
            // the response body provides the http-error information
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    async asyncFindAll() /* :Promise<Scooter[]> */ {
        console.log('ScootersAdaptor.asyncFindAll()...');
        const scooters = await this.fetchJson(this.resourcesUrl);
        // return scooters?.map(Scooter.copyConstructor());
        return scooters?.map(s => Scooter.copyConstructor(s));
    }

    // does not yet implement the query parameters;
    // those will be added later and be merged into the fetch url.
    async asyncFindById(id) /* :Promise<Scooter> */ {
        console.log('ScootersAdaptor.asyncFindById(' + id + ')...');
        const scooterData = await this.fetchJson(this.resourcesUrl + "/" + id);
        return Scooter.copyConstructor(scooterData);
    }

    async asyncSave(scooter) /* :Promise<Scooter> */ {
        console.log('ScootersAdaptor.asyncSave(scooter)');
        return await this.fetchJson(this.resourcesUrl, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            }, body: JSON.stringify(scooter),
        });
    }

    async asyncDeleteById(id) /* :Promise<void> */ {
        console.log('ScootersAdaptor.asyncDeleteById(' + id + ')...');
        return this.fetchJson(this.resourcesUrl + "/" + id,
            {
                method: 'DELETE'
            });
    }
}
