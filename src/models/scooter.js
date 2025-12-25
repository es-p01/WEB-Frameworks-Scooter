export class Scooter {
    id;
    tag;
    status;
    gpsLocation;
    mileage;
    batteryCharge;

    static Status = {
        IDLE: "IDLE",
        INUSE: "INUSE",
        MAINTENANCE: "MAINTENANCE"
    };

    constructor(id, tag, status, gpsLocation, mileage, batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }

    static createSampleScooter(pId = 0) {
        const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        let tag = "";
        for (let i = 0; i < 8; i++) {
            tag += chars.charAt(Math.round(Math.random() * chars.length));
        }
        const status = Object.values(Scooter.Status)[Math.floor(Math.random() * 3)];
        const lat = 52.30 + Math.random() * 0.15;
        const lon = 4.80 + Math.random() * 0.20;
        const gpsLocation = { lat, lon };
        const mileage = Math.round(Math.random() * 10000);
        const batteryCharge = Math.round(5 + Math.random() * 96);

        return new Scooter(pId, tag, status, gpsLocation, mileage, batteryCharge);
    }

    equals(other) {
        if (!other) return false;
        return this.id === other.id &&
            this.tag === other.tag &&
            this.status === other.status &&
            this.batteryCharge === other.batteryCharge &&
            this.mileage === other.mileage &&
            this.gpsLocation === other.gpsLocation;
    }

    // Object.assign copies automatically all the attributes from one instance to the other
    static copyConstructor(scooter) {
        if (scooter === null || scooter === undefined) return null;
        return Object.assign(new Scooter(0), scooter)
    }

    static createSampleNewScooter(pId=0) {
        return new Scooter(pId, "","Unspecified","","","");
    }
}
