import { Routes } from "@angular/router";
import { OffersComponent } from "./pages/offers/offers.component";

export const OffersRoutes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: OffersComponent
    } 

]