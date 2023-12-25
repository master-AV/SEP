import { TestBed } from '@angular/core/testing';

import { CreditCardService } from './credit-card.service';

describe('PaymentMethodService', () => {
  let service: CreditCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreditCardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
