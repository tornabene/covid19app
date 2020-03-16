import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { FarmacoUpdateComponent } from 'app/entities/farmaco/farmaco-update.component';
import { FarmacoService } from 'app/entities/farmaco/farmaco.service';
import { Farmaco } from 'app/shared/model/farmaco.model';

describe('Component Tests', () => {
  describe('Farmaco Management Update Component', () => {
    let comp: FarmacoUpdateComponent;
    let fixture: ComponentFixture<FarmacoUpdateComponent>;
    let service: FarmacoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [FarmacoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FarmacoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FarmacoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FarmacoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Farmaco(123);
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
        const entity = new Farmaco();
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
