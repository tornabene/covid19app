import { IPaziente } from 'app/shared/model/paziente.model';

export interface ISintomo {
  id?: number;
  nome?: string;
  pazientis?: IPaziente[];
}

export class Sintomo implements ISintomo {
  constructor(public id?: number, public nome?: string, public pazientis?: IPaziente[]) {}
}
