import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFarmaco } from 'app/shared/model/farmaco.model';

@Component({
  selector: 'jhi-farmaco-detail',
  templateUrl: './farmaco-detail.component.html'
})
export class FarmacoDetailComponent implements OnInit {
  farmaco: IFarmaco | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farmaco }) => (this.farmaco = farmaco));
  }

  previousState(): void {
    window.history.back();
  }
}
