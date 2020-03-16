import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaziente } from 'app/shared/model/paziente.model';

@Component({
  selector: 'jhi-paziente-detail',
  templateUrl: './paziente-detail.component.html'
})
export class PazienteDetailComponent implements OnInit {
  paziente: IPaziente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paziente }) => (this.paziente = paziente));
  }

  previousState(): void {
    window.history.back();
  }
}
