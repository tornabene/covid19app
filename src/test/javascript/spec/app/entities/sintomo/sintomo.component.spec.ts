import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Covid19AppTestModule } from '../../../test.module';
import { SintomoComponent } from 'app/entities/sintomo/sintomo.component';
import { SintomoService } from 'app/entities/sintomo/sintomo.service';
import { Sintomo } from 'app/shared/model/sintomo.model';

describe('Component Tests', () => {
  describe('Sintomo Management Component', () => {
    let comp: SintomoComponent;
    let fixture: ComponentFixture<SintomoComponent>;
    let service: SintomoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [SintomoComponent]
      })
        .overrideTemplate(SintomoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SintomoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SintomoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sintomo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sintomos && comp.sintomos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
