import React, {useEffect} from 'react';
import {Form} from 'antd';
import {${entityDTO}} from '../data';
import {ModalForm, ProFormGroup, ProFormText, ProFormTextArea} from "@ant-design/pro-form";

export type UpdateFormProps = {
onCancel: (flag?: boolean, formVals?: ${entityDTO}) => void;
onSubmit: (values: ${entityDTO}) => Promise
<void>;
    updateModalVisible: boolean;
    values: Partial<${entityDTO}>;
    };

    const UpdateForm: React.FC
    <UpdateFormProps> = (props) => {
        const {onCancel, onSubmit, updateModalVisible, values} = props;

        const [form] = Form.useForm();

        useEffect(() => {
        form.setFieldsValue(values);
        }, [values, form]);

        return (
        <ModalForm
                width={800}
                title="修改数据"
                visible={updateModalVisible}
                onFinish={onSubmit}
                onVisibleChange={(visible)
        => {
        if (!visible) {
        onCancel();
        }
        }}
        form={form}
        >
        <#list tableStructs as tableStruct>
          <ProFormGroup>
            <ProFormText
              <#if tableStruct.notnull>
                  rules={[{required: true, message: '${tableStruct.fieldNameCn}必填'}]}
              </#if>
            width="sm"
            name="${tableStruct.fieldCamel}"
            label="${tableStruct.fieldNameCn}"
            placeholder="请输入${tableStruct.fieldNameCn}"
            />
          </ProFormGroup>
        </#list>
        </ModalForm>);
        };

        export default UpdateForm;
