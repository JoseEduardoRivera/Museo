import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface IEscultura {
  id?: number;
  altura?: string;
  material?: string;
  estilo?: string;
  peso?: string;
  objArte?: IObjArte | null;
}

export const defaultValue: Readonly<IEscultura> = {};
