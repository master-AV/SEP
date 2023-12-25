import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CardDto} from "../../../credit-card/model/card-dto";

@Component({
  selector: 'app-get-cc-information',
  templateUrl: './get-cc-information.component.html',
  styleUrls: ['./get-cc-information.component.scss']
})
export class GetCcInformationComponent {

  @Input() buttonText: string;
  ccFormGroup: FormGroup
  PANFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])
  securityFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])
  cardHolderNameFormControl = new FormControl('', [Validators.required, Validators.pattern('[A-Za-zČĆŠĐŽčćšđž ]*'), Validators.minLength(2), Validators.maxLength(30)])
  monthFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])
  yearFormControl = new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(2), Validators.maxLength(30)])

  @Output() cardDtoEvent = new EventEmitter<CardDto>();

  ccId: number = 1;
  constructor(private readonly fb: FormBuilder, private route: ActivatedRoute) {

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
      // emit
      this.cardDtoEvent.emit(cc);
    }
  }

  getLastDayOfMonth(year: number, month: number): Date {
    // Create a new Date object with the next month's first day (month + 1)
    // Then, subtract one day to get the last day of the given month
    return new Date(new Date(year, month, 1));//.getDate());
  }
}
