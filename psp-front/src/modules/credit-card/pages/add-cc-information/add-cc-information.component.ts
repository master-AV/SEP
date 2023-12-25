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

  submit(cc: CardDto) {
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
