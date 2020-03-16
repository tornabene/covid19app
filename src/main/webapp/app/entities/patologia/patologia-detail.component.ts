import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatologia } from 'app/shared/model/patologia.model';

@Component({
  selector: 'jhi-patologia-detail',
  templateUrl: './patologia-detail.component.html'
})
export class PatologiaDetailComponent implements OnInit {
  patologia: IPatologia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patologia }) => (this.patologia = patologia));
  }

  previousState(): void {
    window.history.back();
  }
}
