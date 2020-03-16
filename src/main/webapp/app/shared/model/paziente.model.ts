import { Moment } from 'moment';
import { ISintomo } from 'app/shared/model/sintomo.model';
import { IFarmaco } from 'app/shared/model/farmaco.model';
import { IPatologia } from 'app/shared/model/patologia.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IPaziente {
  id?: number;
  nome?: string;
  cf?: string;
  age?: number;
  sex?: Sex;
  comune?: string;
  provincia?: string;
  regione?: string;
  latitudine?: string;
  longitudine?: string;
  numeroFamigliari?: number;
  professione?: string;
  fumatore?: boolean;
  posivito?: boolean;
  isolamentoDomiciliare?: boolean;
  terapiaIntensiva?: boolean;
  guarito?: boolean;
  deceduto?: boolean;
  dataTampone?: Moment;
  dataGuarito?: Moment;
  dataIsolamentoDomiciliare?: Moment;
  dataDeceduto?: Moment;
  dataTerapiaIntensiva?: Moment;
  dataDimissione?: Moment;
  giorniIsolamentoDomiciliare?: number;
  giorniTerapiaIntensiva?: number;
  giorniGuarito?: number;
  giorniDeceduto?: number;
  nota?: string;
  sintomis?: ISintomo[];
  farmaciUsatis?: IFarmaco[];
  altrePatologies?: IPatologia[];
}

export class Paziente implements IPaziente {
  constructor(
    public id?: number,
    public nome?: string,
    public cf?: string,
    public age?: number,
    public sex?: Sex,
    public comune?: string,
    public provincia?: string,
    public regione?: string,
    public latitudine?: string,
    public longitudine?: string,
    public numeroFamigliari?: number,
    public professione?: string,
    public fumatore?: boolean,
    public posivito?: boolean,
    public isolamentoDomiciliare?: boolean,
    public terapiaIntensiva?: boolean,
    public guarito?: boolean,
    public deceduto?: boolean,
    public dataTampone?: Moment,
    public dataGuarito?: Moment,
    public dataIsolamentoDomiciliare?: Moment,
    public dataDeceduto?: Moment,
    public dataTerapiaIntensiva?: Moment,
    public dataDimissione?: Moment,
    public giorniIsolamentoDomiciliare?: number,
    public giorniTerapiaIntensiva?: number,
    public giorniGuarito?: number,
    public giorniDeceduto?: number,
    public nota?: string,
    public sintomis?: ISintomo[],
    public farmaciUsatis?: IFarmaco[],
    public altrePatologies?: IPatologia[]
  ) {
    this.fumatore = this.fumatore || false;
    this.posivito = this.posivito || false;
    this.isolamentoDomiciliare = this.isolamentoDomiciliare || false;
    this.terapiaIntensiva = this.terapiaIntensiva || false;
    this.guarito = this.guarito || false;
    this.deceduto = this.deceduto || false;
  }
}
