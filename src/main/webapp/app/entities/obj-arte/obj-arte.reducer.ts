import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IObjArte, defaultValue } from 'app/shared/model/obj-arte.model';

export const ACTION_TYPES = {
  FETCH_OBJARTE_LIST: 'objArte/FETCH_OBJARTE_LIST',
  FETCH_OBJARTE: 'objArte/FETCH_OBJARTE',
  CREATE_OBJARTE: 'objArte/CREATE_OBJARTE',
  UPDATE_OBJARTE: 'objArte/UPDATE_OBJARTE',
  PARTIAL_UPDATE_OBJARTE: 'objArte/PARTIAL_UPDATE_OBJARTE',
  DELETE_OBJARTE: 'objArte/DELETE_OBJARTE',
  RESET: 'objArte/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IObjArte>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ObjArteState = Readonly<typeof initialState>;

// Reducer

export default (state: ObjArteState = initialState, action): ObjArteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OBJARTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OBJARTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OBJARTE):
    case REQUEST(ACTION_TYPES.UPDATE_OBJARTE):
    case REQUEST(ACTION_TYPES.DELETE_OBJARTE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_OBJARTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OBJARTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OBJARTE):
    case FAILURE(ACTION_TYPES.CREATE_OBJARTE):
    case FAILURE(ACTION_TYPES.UPDATE_OBJARTE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_OBJARTE):
    case FAILURE(ACTION_TYPES.DELETE_OBJARTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OBJARTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_OBJARTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OBJARTE):
    case SUCCESS(ACTION_TYPES.UPDATE_OBJARTE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_OBJARTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OBJARTE):
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

const apiUrl = 'api/obj-artes';

// Actions

export const getEntities: ICrudGetAllAction<IObjArte> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OBJARTE_LIST,
    payload: axios.get<IObjArte>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IObjArte> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OBJARTE,
    payload: axios.get<IObjArte>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IObjArte> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OBJARTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IObjArte> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OBJARTE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IObjArte> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_OBJARTE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IObjArte> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OBJARTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
