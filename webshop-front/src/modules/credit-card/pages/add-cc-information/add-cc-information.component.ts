import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CardDto} from "../../model/card-dto";
import {CreditCardService} from "../../services/credit-card.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-add-cc-information',
  templateUrl: './add-cc-information.component.html',
  styleUrls: ['./add-cc-information.component.scss']
})
export class AddCcInformationComponent {

  ccFormGroup: FormGroup
  PANFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])
  securityFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])
  cardHolderNameFormControl = new FormControl('', [Validators.required, Validators.pattern('[A-Za-zČĆŠĐŽčćšđž ]*'), Validators.minLength(2), Validators.maxLength(30)])
  monthFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])
  yearFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])

  ccId: number = 0;

  constructor(private readonly fb: FormBuilder,
              private route: ActivatedRoute,
              private cardService: CreditCardService) {

    this.route.params.subscribe(
      params => {
        this.ccId = params['id'];
      });

    this.ccFormGroup = fb.group({
      pan: this.PANFormControl,
      securityCode: this.securityFormControl,
      cardHolderName: this.cardHolderNameFormControl,
      month: this.monthFormControl,
      year: this.yearFormControl
    })
  }

  submit() {
    if (this.ccFormGroup.valid && this.PANFormControl.value != null && this.yearFormControl.value != null
        && this.cardHolderNameFormControl.value != null && this.securityFormControl.value != null){
      let cc : CardDto = {
        pan: +this.PANFormControl.value,
        cardHolderName: this.cardHolderNameFormControl.value,
        expirationDate: this.getLastDayOfMonth(+this.yearFormControl.value, +this.monthFormControl.value),// new Date('2024-4-01'),//new Date(+this.yearFormControl.value, +this.monthFormControl.value, 31)
        securityCode: +this.securityFormControl.value,
        paymentId: this.ccId
      };
      console.log(cc);
      this.cardService.sendInformation(cc).subscribe(
        response => {
          console.log("--------------------");
          console.log("Send info")
          console.log(response);
          // console.log(response.headers.get('Location'))
          window.location.href = response.headers.get('Location')

        }
      )

    }
  }

  getLastDayOfMonth(year: number, month: number): Date {
    // Create a new Date object with the next month's first day (month + 1)
    // Then, subtract one day to get the last day of the given month
    return new Date(new Date(year, month, 1));//.getDate());
  }
}
