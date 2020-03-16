import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { PazienteUpdateComponent } from 'app/entities/paziente/paziente-update.component';
import { PazienteService } from 'app/entities/paziente/paziente.service';
import { Paziente } from 'app/shared/model/paziente.model';

describe('Component Tests', () => {
  describe('Paziente Management Update Component', () => {
    let comp: PazienteUpdateComponent;
    let fixture: ComponentFixture<PazienteUpdateComponent>;
    let service: PazienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [PazienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PazienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PazienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PazienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paziente(123);
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
        const entity = new Paziente();
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
