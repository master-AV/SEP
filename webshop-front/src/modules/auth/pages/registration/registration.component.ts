import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  ValidatorFn,
  Validators
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { map, Observable, startWith, Subscription } from 'rxjs';
// import { matchPasswordsValidator } from 'src/modules/shared/validators/confirm-password.validator';
// import { ToastrService } from 'ngx-toastr';
import { RegularUserRegistration } from '../../model/registration_and_verification/regular-user-registration';
import { Router } from '@angular/router';
import { UserService } from 'src/modules/auth/service/user-service/user.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  showSpiner: boolean = false;
  hidePassword = true;
  hideConfirmPassword = true;
  regularUserRoles = ['TENANT', 'OWNER'];
  registrationSubscription: Subscription;
  registrationForm: FormGroup;

  constructor(
    // private toast: ToastrService,
    private router: Router,
    private userService: UserService
  ) {
    // this.filteredCities = this.registrationForm
    //   .get('cityFormControl')
    //   .valueChanges.pipe(
    //     startWith(''),
    //     map(city => (city ? this._filterCities(city) : this.cities.slice()))
    //   );

    //   this.filteredCountries = this.registrationForm.get('countryFormControl')?.valueChanges.pipe(
    //     startWith(''),
    //     map((country: string) => (country ? this._filterCountries(country) : this.countries.slice()))
    //   );
    this.showSpiner = false;
  }

  ngOnInit(): void {
    let passwordFormControl = new FormControl('', [
      Validators.required,
      Validators.minLength(12),
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{12,100}$')
    ]);
    this.registrationForm = new FormGroup(
      {
        emailFormControl: new FormControl('', [
          Validators.required,
          Validators.email,
        ]),
        nameFormControl: new FormControl('', [
          Validators.required,
          Validators.pattern('[a-zA-Z ]*'),
        ]),
        surnameFormControl: new FormControl('', [
          Validators.required,
          Validators.pattern('[a-zA-Z ]*'),
        ]),
        passwordFormControl: passwordFormControl,
        passwordAgainFormControl: new FormControl('', [
          Validators.required,
          Validators.minLength(12),
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{12,100}$')
        ]),
        roleFormControl: new FormControl('', [
          Validators.required,
        ]),
      },
      [this.createPasswordMatchingValidator(passwordFormControl)]
    );
  }

  matcher = new MyErrorStateMatcher();

  register(): void {
    if (this.registrationForm.hasError('mismatch')) {
      // this.toast.error('Passwords not match');
    } else if (this.registrationForm.invalid) {
      // this.toast.error('Registration form is invalid.')
    } else {
      const newUser: RegularUserRegistration = {
        confirmPassword: "",
        email: "",
        name: "",
        password: "",
        role: "",
        surname: ""
        // email: this.registrationForm.get('emailFormControl').value,
        // name: this.registrationForm.get('nameFormControl').value,
        // surname: this.registrationForm.get('surnameFormControl').value,
        // password: this.registrationForm.get('passwordFormControl').value,
        // confirmPassword: this.registrationForm.get('passwordAgainFormControl').value,
        // role: 'ROLE_'+this.registrationForm.get('roleFormControl').value
      }

      this.showSpiner = true;
      this.registrationSubscription = this.userService
            .registerRegularUser(newUser)
            .subscribe(
              response => {
                this.showSpiner = false;
                // this.toast.success(
                //   'Please go to your email to verify account!',
                //   'Registration successfully'
                // );
                this.router.navigate([`/smart-home/auth/login`]);
              },
              error => {
                this.showSpiner = false;
                // this.toast.error(error.error, 'Registration failed')
              }
            );
    }
  }

  getError() {
    return this.registrationForm.hasError('mismatch');
  }

  // _filterCountries(value: string): Country[] {
  //   const filterValue = value.toLowerCase();

  //   return this.countries.filter(country => country.name.toLowerCase().includes(filterValue));
  // }

  // _filterCities(value: string): string[] {
  //   const filterValue = value.toLowerCase();

  //   return this.cities.filter(city => city.toLowerCase().includes(filterValue));
  // }

  ngOnDestroy(): void {
    if (this.registrationSubscription) {
      this.registrationSubscription.unsubscribe();
    }
  }


  createPasswordMatchingValidator (passwordFormControl: FormControl): ValidatorFn {
    {
      return (control: AbstractControl): Record<string, string> | null =>
        control.value === passwordFormControl.value
          ? null
          : { wrongValue: control.value }
    }
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }

}
