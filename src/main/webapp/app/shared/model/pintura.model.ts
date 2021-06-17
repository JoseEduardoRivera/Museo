import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface IPintura {
  id?: number;
  tipoPintura?: string;
  materialPintura?: string;
  estiloPint?: string;
  objArte?: IObjArte | null;
}

export const defaultValue: Readonly<IPintura> = {};
