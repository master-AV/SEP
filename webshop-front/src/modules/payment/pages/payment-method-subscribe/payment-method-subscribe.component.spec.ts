import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentMethodSubscribeComponent } from './payment-method-subscribe.component';

describe('PaymentMethodSubscribeComponent', () => {
  let component: PaymentMethodSubscribeComponent;
  let fixture: ComponentFixture<PaymentMethodSubscribeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentMethodSubscribeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodSubscribeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
