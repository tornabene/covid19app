import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISintomo } from 'app/shared/model/sintomo.model';

@Component({
  selector: 'jhi-sintomo-detail',
  templateUrl: './sintomo-detail.component.html'
})
export class SintomoDetailComponent implements OnInit {
  sintomo: ISintomo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sintomo }) => (this.sintomo = sintomo));
  }

  previousState(): void {
    window.history.back();
  }
}
