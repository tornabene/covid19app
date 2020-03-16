import { IPaziente } from 'app/shared/model/paziente.model';

export interface IFarmaco {
  id?: number;
  nome?: string;
  pazientis?: IPaziente[];
}

export class Farmaco implements IFarmaco {
  constructor(public id?: number, public nome?: string, public pazientis?: IPaziente[]) {}
}
