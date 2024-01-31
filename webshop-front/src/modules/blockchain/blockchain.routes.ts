import {Routes} from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import {AddWalletComponent} from "./pages/add-wallet/add-wallet.component";

export const BlockchainRoutes: Routes = [
  {
    path: 'add-wallet',
    pathMatch: 'full',
    component: AddWalletComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_USER' }
  },
]
