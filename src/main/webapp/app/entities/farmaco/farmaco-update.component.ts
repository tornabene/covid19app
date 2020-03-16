import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFarmaco, Farmaco } from 'app/shared/model/farmaco.model';
import { FarmacoService } from './farmaco.service';

@Component({
  selector: 'jhi-farmaco-update',
  templateUrl: './farmaco-update.component.html'
})
export class FarmacoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected farmacoService: FarmacoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farmaco }) => {
      this.updateForm(farmaco);
    });
  }

  updateForm(farmaco: IFarmaco): void {
    this.editForm.patchValue({
      id: farmaco.id,
      nome: farmaco.nome
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const farmaco = this.createFromForm();
    if (farmaco.id !== undefined) {
      this.subscribeToSaveResponse(this.farmacoService.update(farmaco));
    } else {
      this.subscribeToSaveResponse(this.farmacoService.create(farmaco));
    }
  }

  private createFromForm(): IFarmaco {
    return {
      ...new Farmaco(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFarmaco>>): void {
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
