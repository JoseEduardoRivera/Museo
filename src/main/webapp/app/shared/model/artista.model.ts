import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface IArtista {
  id?: number;
  nomArt?: string;
  fechNac?: string;
  fechDefu?: string | null;
  paisOrigen?: string;
  estiloArt?: string;
  epoca?: string;
  objArtes?: IObjArte[] | null;
}

export const defaultValue: Readonly<IArtista> = {};
