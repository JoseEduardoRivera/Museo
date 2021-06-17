import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface ICollecPerma {
  id?: number;
  exhibiAlmacen?: string;
  costo?: string;
  fecha?: string;
  objArte?: IObjArte | null;
}

export const defaultValue: Readonly<ICollecPerma> = {};
