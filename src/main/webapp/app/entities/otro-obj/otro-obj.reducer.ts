import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOtroObj, defaultValue } from 'app/shared/model/otro-obj.model';

export const ACTION_TYPES = {
  FETCH_OTROOBJ_LIST: 'otroObj/FETCH_OTROOBJ_LIST',
  FETCH_OTROOBJ: 'otroObj/FETCH_OTROOBJ',
  CREATE_OTROOBJ: 'otroObj/CREATE_OTROOBJ',
  UPDATE_OTROOBJ: 'otroObj/UPDATE_OTROOBJ',
  PARTIAL_UPDATE_OTROOBJ: 'otroObj/PARTIAL_UPDATE_OTROOBJ',
  DELETE_OTROOBJ: 'otroObj/DELETE_OTROOBJ',
  RESET: 'otroObj/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOtroObj>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OtroObjState = Readonly<typeof initialState>;

// Reducer

export default (state: OtroObjState = initialState, action): OtroObjState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OTROOBJ_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OTROOBJ):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OTROOBJ):
    case REQUEST(ACTION_TYPES.UPDATE_OTROOBJ):
    case REQUEST(ACTION_TYPES.DELETE_OTROOBJ):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_OTROOBJ):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OTROOBJ_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OTROOBJ):
    case FAILURE(ACTION_TYPES.CREATE_OTROOBJ):
    case FAILURE(ACTION_TYPES.UPDATE_OTROOBJ):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_OTROOBJ):
    case FAILURE(ACTION_TYPES.DELETE_OTROOBJ):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OTROOBJ_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_OTROOBJ):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OTROOBJ):
    case SUCCESS(ACTION_TYPES.UPDATE_OTROOBJ):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_OTROOBJ):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OTROOBJ):
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

const apiUrl = 'api/otro-objs';

// Actions

export const getEntities: ICrudGetAllAction<IOtroObj> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OTROOBJ_LIST,
    payload: axios.get<IOtroObj>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOtroObj> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OTROOBJ,
    payload: axios.get<IOtroObj>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOtroObj> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OTROOBJ,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOtroObj> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OTROOBJ,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOtroObj> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_OTROOBJ,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOtroObj> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OTROOBJ,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
