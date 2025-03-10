import { TestBed } from '@angular/core/testing';

import { EmploidutempService } from './emploidutemp.service';

describe('EmploidutempService', () => {
  let service: EmploidutempService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmploidutempService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
