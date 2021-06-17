import { IObjArte } from 'app/shared/model/obj-arte.model';

export interface ICollecPresta {
  id?: number;
  infPrest?: string;
  fechPrest?: string;
  fechDev?: string;
  objArte?: IObjArte | null;
}

export const defaultValue: Readonly<ICollecPresta> = {};
