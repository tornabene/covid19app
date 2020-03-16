import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { PatologiaUpdateComponent } from 'app/entities/patologia/patologia-update.component';
import { PatologiaService } from 'app/entities/patologia/patologia.service';
import { Patologia } from 'app/shared/model/patologia.model';

describe('Component Tests', () => {
  describe('Patologia Management Update Component', () => {
    let comp: PatologiaUpdateComponent;
    let fixture: ComponentFixture<PatologiaUpdateComponent>;
    let service: PatologiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [PatologiaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatologiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatologiaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatologiaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Patologia(123);
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
        const entity = new Patologia();
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
