import {Routes} from "@angular/router";
import {AddCcInformationComponent} from "./pages/add-cc-information/add-cc-information.component";

export const CreditCardRoutes: Routes = [
  {
    path: ':id',
    pathMatch: 'full',
    component: AddCcInformationComponent
  },
]
