import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISintomo, Sintomo } from 'app/shared/model/sintomo.model';
import { SintomoService } from './sintomo.service';

@Component({
  selector: 'jhi-sintomo-update',
  templateUrl: './sintomo-update.component.html'
})
export class SintomoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected sintomoService: SintomoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sintomo }) => {
      this.updateForm(sintomo);
    });
  }

  updateForm(sintomo: ISintomo): void {
    this.editForm.patchValue({
      id: sintomo.id,
      nome: sintomo.nome
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sintomo = this.createFromForm();
    if (sintomo.id !== undefined) {
      this.subscribeToSaveResponse(this.sintomoService.update(sintomo));
    } else {
      this.subscribeToSaveResponse(this.sintomoService.create(sintomo));
    }
  }

  private createFromForm(): ISintomo {
    return {
      ...new Sintomo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISintomo>>): void {
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
