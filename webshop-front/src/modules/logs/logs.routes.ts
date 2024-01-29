import {Routes} from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { LogsTableComponent } from "./pages/logs-table/logs-table.component";

export const LogsRoutes: Routes = [
  {
    path: 'logs',
    pathMatch: 'full',
    component: LogsTableComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_ADMIN' }
  }
]
