import { Component } from '@angular/core';
import {QrService} from "../../services/qr.service";
import {CardDto} from "../../../credit-card/model/card-dto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-save-cc-information',
  templateUrl: './save-cc-information.component.html',
  styleUrls: ['./save-cc-information.component.scss']
})
export class SaveCcInformationComponent {

  constructor(private qr: QrService, private router: Router) {
  }

  submit(cc: CardDto) {
    console.log(cc);
    this.qr.saveCardDto(cc).subscribe(
      response => {
        console.log(response)
        console.log("SAVED cc")
        window.location.href = "http://localhost:4200/qr/" + cc.paymentId;
        // this.router.navigateByUrl(`/psp/qr/${cc.paymentId}`);
      }
    )
  }
}
