import {Routes} from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { AllTransactionsComponent } from "./pages/all-transactions/all-transactions.component";

export const TransactionsRoutes: Routes = [
  {
    path: 'transactions',
    pathMatch: 'full',
    component: AllTransactionsComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_ADMIN' }
  }
]
