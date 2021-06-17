import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface IExhibicion {
  id?: number;
  nomExi?: string;
  fechIni?: string;
  fechFin?: string;
  objArtes?: IObjArte[] | null;
}

export const defaultValue: Readonly<IExhibicion> = {};
