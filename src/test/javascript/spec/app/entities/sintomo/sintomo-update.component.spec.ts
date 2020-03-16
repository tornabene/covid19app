import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { SintomoUpdateComponent } from 'app/entities/sintomo/sintomo-update.component';
import { SintomoService } from 'app/entities/sintomo/sintomo.service';
import { Sintomo } from 'app/shared/model/sintomo.model';

describe('Component Tests', () => {
  describe('Sintomo Management Update Component', () => {
    let comp: SintomoUpdateComponent;
    let fixture: ComponentFixture<SintomoUpdateComponent>;
    let service: SintomoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [SintomoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SintomoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SintomoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SintomoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sintomo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sintomo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
