import {createRouter, createWebHashHistory} from "vue-router";

import WelcomeComponent from "@/components/WelcomeComponent";
import ScootersOverview31 from "@/components/scooters/ScootersOverview31";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";
import ScootersDetail32 from "@/components/scooters/ScootersDetail32";
import UnknownRoute from "@/components/UnknownRoute";
import ScootersOverview33 from "@/components/scooters/ScootersOverview33";
import ScootersDetail34 from "@/components/scooters/ScootersDetail34";
import ScootersOverview37 from "@/components/scooters/ScootersOverview37";
import ScootersDetail37 from "@/components/scooters/ScootersDetail37";
import SignIn from "@/components/SignIn";

const routes = [
    {path: '/', component: WelcomeComponent},
    {path: '/home', component: WelcomeComponent},
    {path: '/scooters/overview31', component: ScootersOverview31},
    {path: '/scooters/overview32', component: ScootersOverview32},
    {
        path: '/scooters/overview33', component: ScootersOverview33, children: [
            {path: ':id', component: ScootersDetail32}
        ]
    },

    {path: '/unknown', component: UnknownRoute},
    {path: '/:pathMatch(.*)', redirect: '/unknown'},
    {
        path: '/scooters/overview34', component: ScootersOverview33, children: [
            {path: ':id', component: ScootersDetail34}
        ]
    },
    {
        path: '/scooters/overview37', component: ScootersOverview37, children: [
            {path: ':id', component: ScootersDetail37}
        ]
    },
    {path: '/sign-in', component: SignIn},
    {path: '/sign-out', redirect: {path: '/sign-in', query: {signOut: 'true'}}},


]

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})


