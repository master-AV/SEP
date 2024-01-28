import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CreditCardService} from "../../../credit-card/services/credit-card.service";
import {CardDto} from "../../../credit-card/model/card-dto";
import {WalletDto} from "../model/wallet-dto";
import {WalletService} from "../service/wallet.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-add-wallet',
  templateUrl: './add-wallet.component.html',
  styleUrls: ['./add-wallet.component.scss']
})
export class AddWalletComponent {

  walletFormGroup: FormGroup
  accountId = new FormControl('', [Validators.required, Validators.pattern('[0-9.]*'), Validators.minLength(2), Validators.maxLength(30)])
  accountKey = new FormControl('', [Validators.required, Validators.pattern('[a-z0-9]*'), Validators.minLength(2), Validators.maxLength(300)])


  ccId: number = 0;

  constructor(private readonly fb: FormBuilder, private location: Location,
              private walletService: WalletService) {
    this.walletFormGroup = fb.group({
      accountId: this.accountId,
      accountKey: this.accountKey,
    })
  }

  submit() {
    if (this.walletFormGroup.valid && this.accountId.value != null && this.accountKey.value != null){
      let wallet : WalletDto = {
        accountId: this.accountId.value, accountKey: this.accountKey.value, userId: 10
      };
      console.log(wallet);
      this.walletService.createWallet(wallet).subscribe(
        response => {
          console.log("--------------------");
          console.log("Send info")
          console.log(response);
          // console.log(response.headers.get('Location'))

          this.location.back();
        }
      )

    }
  }
}
