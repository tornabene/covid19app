import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPaziente, Paziente } from 'app/shared/model/paziente.model';
import { PazienteService } from './paziente.service';
import { ISintomo } from 'app/shared/model/sintomo.model';
import { SintomoService } from 'app/entities/sintomo/sintomo.service';
import { IFarmaco } from 'app/shared/model/farmaco.model';
import { FarmacoService } from 'app/entities/farmaco/farmaco.service';
import { IPatologia } from 'app/shared/model/patologia.model';
import { PatologiaService } from 'app/entities/patologia/patologia.service';

type SelectableEntity = ISintomo | IFarmaco | IPatologia;

@Component({
  selector: 'jhi-paziente-update',
  templateUrl: './paziente-update.component.html'
})
export class PazienteUpdateComponent implements OnInit {
  isSaving = false;
  sintomos: ISintomo[] = [];
  farmacos: IFarmaco[] = [];
  patologias: IPatologia[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [],
    cf: [],
    age: [],
    sex: [],
    comune: [],
    provincia: [],
    regione: [],
    latitudine: [],
    longitudine: [],
    numeroFamigliari: [],
    professione: [],
    fumatore: [],
    posivito: [],
    isolamentoDomiciliare: [],
    terapiaIntensiva: [],
    guarito: [],
    deceduto: [],
    dataTampone: [],
    dataGuarito: [],
    dataIsolamentoDomiciliare: [],
    dataDeceduto: [],
    dataTerapiaIntensiva: [],
    dataDimissione: [],
    giorniIsolamentoDomiciliare: [],
    giorniTerapiaIntensiva: [],
    giorniGuarito: [],
    giorniDeceduto: [],
    nota: [],
    sintomis: [],
    farmaciUsatis: [],
    altrePatologies: []
  });

  constructor(
    protected pazienteService: PazienteService,
    protected sintomoService: SintomoService,
    protected farmacoService: FarmacoService,
    protected patologiaService: PatologiaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paziente }) => {
      if (!paziente.id) {
        const today = moment().startOf('day');
        paziente.dataTampone = today;
        paziente.dataGuarito = today;
        paziente.dataIsolamentoDomiciliare = today;
        paziente.dataDeceduto = today;
        paziente.dataTerapiaIntensiva = today;
        paziente.dataDimissione = today;
      }

      this.updateForm(paziente);

      this.sintomoService.query().subscribe((res: HttpResponse<ISintomo[]>) => (this.sintomos = res.body || []));

      this.farmacoService.query().subscribe((res: HttpResponse<IFarmaco[]>) => (this.farmacos = res.body || []));

      this.patologiaService.query().subscribe((res: HttpResponse<IPatologia[]>) => (this.patologias = res.body || []));
    });
  }

  updateForm(paziente: IPaziente): void {
    this.editForm.patchValue({
      id: paziente.id,
      nome: paziente.nome,
      cf: paziente.cf,
      age: paziente.age,
      sex: paziente.sex,
      comune: paziente.comune,
      provincia: paziente.provincia,
      regione: paziente.regione,
      latitudine: paziente.latitudine,
      longitudine: paziente.longitudine,
      numeroFamigliari: paziente.numeroFamigliari,
      professione: paziente.professione,
      fumatore: paziente.fumatore,
      posivito: paziente.posivito,
      isolamentoDomiciliare: paziente.isolamentoDomiciliare,
      terapiaIntensiva: paziente.terapiaIntensiva,
      guarito: paziente.guarito,
      deceduto: paziente.deceduto,
      dataTampone: paziente.dataTampone ? paziente.dataTampone.format(DATE_TIME_FORMAT) : null,
      dataGuarito: paziente.dataGuarito ? paziente.dataGuarito.format(DATE_TIME_FORMAT) : null,
      dataIsolamentoDomiciliare: paziente.dataIsolamentoDomiciliare ? paziente.dataIsolamentoDomiciliare.format(DATE_TIME_FORMAT) : null,
      dataDeceduto: paziente.dataDeceduto ? paziente.dataDeceduto.format(DATE_TIME_FORMAT) : null,
      dataTerapiaIntensiva: paziente.dataTerapiaIntensiva ? paziente.dataTerapiaIntensiva.format(DATE_TIME_FORMAT) : null,
      dataDimissione: paziente.dataDimissione ? paziente.dataDimissione.format(DATE_TIME_FORMAT) : null,
      giorniIsolamentoDomiciliare: paziente.giorniIsolamentoDomiciliare,
      giorniTerapiaIntensiva: paziente.giorniTerapiaIntensiva,
      giorniGuarito: paziente.giorniGuarito,
      giorniDeceduto: paziente.giorniDeceduto,
      nota: paziente.nota,
      sintomis: paziente.sintomis,
      farmaciUsatis: paziente.farmaciUsatis,
      altrePatologies: paziente.altrePatologies
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paziente = this.createFromForm();
    if (paziente.id !== undefined) {
      this.subscribeToSaveResponse(this.pazienteService.update(paziente));
    } else {
      this.subscribeToSaveResponse(this.pazienteService.create(paziente));
    }
  }

  private createFromForm(): IPaziente {
    return {
      ...new Paziente(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cf: this.editForm.get(['cf'])!.value,
      age: this.editForm.get(['age'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      comune: this.editForm.get(['comune'])!.value,
      provincia: this.editForm.get(['provincia'])!.value,
      regione: this.editForm.get(['regione'])!.value,
      latitudine: this.editForm.get(['latitudine'])!.value,
      longitudine: this.editForm.get(['longitudine'])!.value,
      numeroFamigliari: this.editForm.get(['numeroFamigliari'])!.value,
      professione: this.editForm.get(['professione'])!.value,
      fumatore: this.editForm.get(['fumatore'])!.value,
      posivito: this.editForm.get(['posivito'])!.value,
      isolamentoDomiciliare: this.editForm.get(['isolamentoDomiciliare'])!.value,
      terapiaIntensiva: this.editForm.get(['terapiaIntensiva'])!.value,
      guarito: this.editForm.get(['guarito'])!.value,
      deceduto: this.editForm.get(['deceduto'])!.value,
      dataTampone: this.editForm.get(['dataTampone'])!.value
        ? moment(this.editForm.get(['dataTampone'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataGuarito: this.editForm.get(['dataGuarito'])!.value
        ? moment(this.editForm.get(['dataGuarito'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataIsolamentoDomiciliare: this.editForm.get(['dataIsolamentoDomiciliare'])!.value
        ? moment(this.editForm.get(['dataIsolamentoDomiciliare'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataDeceduto: this.editForm.get(['dataDeceduto'])!.value
        ? moment(this.editForm.get(['dataDeceduto'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataTerapiaIntensiva: this.editForm.get(['dataTerapiaIntensiva'])!.value
        ? moment(this.editForm.get(['dataTerapiaIntensiva'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataDimissione: this.editForm.get(['dataDimissione'])!.value
        ? moment(this.editForm.get(['dataDimissione'])!.value, DATE_TIME_FORMAT)
        : undefined,
      giorniIsolamentoDomiciliare: this.editForm.get(['giorniIsolamentoDomiciliare'])!.value,
      giorniTerapiaIntensiva: this.editForm.get(['giorniTerapiaIntensiva'])!.value,
      giorniGuarito: this.editForm.get(['giorniGuarito'])!.value,
      giorniDeceduto: this.editForm.get(['giorniDeceduto'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      sintomis: this.editForm.get(['sintomis'])!.value,
      farmaciUsatis: this.editForm.get(['farmaciUsatis'])!.value,
      altrePatologies: this.editForm.get(['altrePatologies'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaziente>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
