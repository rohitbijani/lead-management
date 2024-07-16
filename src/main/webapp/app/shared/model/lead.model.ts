import dayjs from 'dayjs';

export interface ILead {
  id?: number;
  name?: string;
  phone?: number;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
}

export const defaultValue: Readonly<ILead> = {};
