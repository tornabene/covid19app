import { IPaziente } from 'app/shared/model/paziente.model';

export interface IPatologia {
  id?: number;
  nome?: string;
  pazientis?: IPaziente[];
}

export class Patologia implements IPatologia {
  constructor(public id?: number, public nome?: string, public pazientis?: IPaziente[]) {}
}
