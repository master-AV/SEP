import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { HomeComponent } from "./pages/home/home.component";

export const OffersRoutes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: HomeComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN|ROLE_USER' }
    } 

]