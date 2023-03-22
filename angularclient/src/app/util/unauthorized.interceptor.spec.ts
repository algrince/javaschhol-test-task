import { TestBed } from '@angular/core/testing';

import { UnathorizedInterceptor } from './unathorized.interceptor';

describe('UnathorizedInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      UnathorizedInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: UnathorizedInterceptor = TestBed.inject(UnathorizedInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
