import { Routes } from "@angular/router";
import { OffersComponent } from "./pages/offers/offers.component";
import { RoleGuard } from "../auth/guards/role/role.guard";

export const OffersRoutes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: OffersComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN|ROLE_USER' }
    } 

]