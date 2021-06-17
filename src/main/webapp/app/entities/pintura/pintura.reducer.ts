import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPintura, defaultValue } from 'app/shared/model/pintura.model';

export const ACTION_TYPES = {
  FETCH_PINTURA_LIST: 'pintura/FETCH_PINTURA_LIST',
  FETCH_PINTURA: 'pintura/FETCH_PINTURA',
  CREATE_PINTURA: 'pintura/CREATE_PINTURA',
  UPDATE_PINTURA: 'pintura/UPDATE_PINTURA',
  PARTIAL_UPDATE_PINTURA: 'pintura/PARTIAL_UPDATE_PINTURA',
  DELETE_PINTURA: 'pintura/DELETE_PINTURA',
  RESET: 'pintura/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPintura>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PinturaState = Readonly<typeof initialState>;

// Reducer

export default (state: PinturaState = initialState, action): PinturaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PINTURA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PINTURA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PINTURA):
    case REQUEST(ACTION_TYPES.UPDATE_PINTURA):
    case REQUEST(ACTION_TYPES.DELETE_PINTURA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PINTURA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PINTURA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PINTURA):
    case FAILURE(ACTION_TYPES.CREATE_PINTURA):
    case FAILURE(ACTION_TYPES.UPDATE_PINTURA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PINTURA):
    case FAILURE(ACTION_TYPES.DELETE_PINTURA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PINTURA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PINTURA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PINTURA):
    case SUCCESS(ACTION_TYPES.UPDATE_PINTURA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PINTURA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PINTURA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/pinturas';

// Actions

export const getEntities: ICrudGetAllAction<IPintura> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PINTURA_LIST,
    payload: axios.get<IPintura>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPintura> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PINTURA,
    payload: axios.get<IPintura>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPintura> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PINTURA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPintura> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PINTURA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPintura> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PINTURA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPintura> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PINTURA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
