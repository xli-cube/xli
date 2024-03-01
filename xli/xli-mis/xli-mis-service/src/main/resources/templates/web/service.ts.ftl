import type {${entityDTO}} from './data';
import request from "@/utils/request";
import type {BaseParam, ResultVO} from "@/services/data";

export async function search(
params?: ${entityDTO} & BaseParam
) {
return request
<ResultVO>('/rest/${_entity}/search', {
    method: 'POST',
    data: params
    });
    }

    export async function add(data: ${entityDTO}) {
    return request
    <ResultVO>('/rest/${_entity}/add', {
        method: 'POST',
        data
        });
        }

        export async function remove(dto: ${entityDTO}[]) {
        const ids = dto.filter(row => row.id !== undefined).map(row => row.id!);
        return request
        <ResultVO>('/rest/${_entity}/delete', {
            method: 'POST',
            data: ids
            });
            }

            export async function update(data: ${entityDTO}) {
            return request
            <ResultVO>('/rest/${_entity}/update', {
                method: 'POST',
                data
                });
                }
