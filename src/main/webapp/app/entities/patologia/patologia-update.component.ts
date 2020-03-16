import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPatologia, Patologia } from 'app/shared/model/patologia.model';
import { PatologiaService } from './patologia.service';

@Component({
  selector: 'jhi-patologia-update',
  templateUrl: './patologia-update.component.html'
})
export class PatologiaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected patologiaService: PatologiaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patologia }) => {
      this.updateForm(patologia);
    });
  }

  updateForm(patologia: IPatologia): void {
    this.editForm.patchValue({
      id: patologia.id,
      nome: patologia.nome
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patologia = this.createFromForm();
    if (patologia.id !== undefined) {
      this.subscribeToSaveResponse(this.patologiaService.update(patologia));
    } else {
      this.subscribeToSaveResponse(this.patologiaService.create(patologia));
    }
  }

  private createFromForm(): IPatologia {
    return {
      ...new Patologia(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatologia>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
