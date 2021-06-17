import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface IOtroObj {
  id?: number;
  tipo?: string;
  estilo?: string;
  objArte?: IObjArte | null;
}

export const defaultValue: Readonly<IOtroObj> = {};
