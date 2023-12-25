import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {CreditCardService} from "../../../credit-card/services/credit-card.service";

@Component({
  selector: 'app-payment-state',
  templateUrl: './pay-success.component.html',
  styleUrls: ['./pay-success.component.scss']
})
export class PaySuccessComponent implements OnInit {
  img: string;
  state: string

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        switch(params['state']){
          case 'succ':
            this.img = "assets/check.png";
            this.state = 'Transakcija je uspešno obavljena'
            break;
          case 'failed':
            this.img = "assets/close.png";
            this.state = 'Transakciju nije moguće izvršiti'
            break;
          default:
            this.img = "assets/warning.png";
            this.state = 'Greška se desilo i transkacija nije mogla biti izvršena'
            break;
        }
      });
  }

}
