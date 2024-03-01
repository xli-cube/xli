import React from 'react';
import {ModalForm, ProFormGroup, ProFormText, ProFormTextArea} from "@ant-design/pro-form";
import {${entityDTO}} from "../data";
import {Form} from "antd";

type CreateFormProps = {
modalVisible: boolean; onCancel: () => void; onSubmit: (values: ${entityDTO}) => Promise
<boolean>;
    };

    const CreateForm: React.FC
    <CreateFormProps> = (props) => {
        const {modalVisible, onCancel, onSubmit} = props;
        const [form] = Form.useForm();

        const handleFormSubmit = async () => {
        try {
        const values = await form.validateFields();
        const success = await onSubmit(values);
        if (success) {
        form.resetFields();
        }
        } catch (error) {
        console.error("表单提交出错:", error);
        }
        };

        return (
        <ModalForm
                title="添加数据"
                width={800}
                form={form}
                visible={modalVisible}
                onVisibleChange={(visible)
        => {
        if (!visible) {
        onCancel();
        }
        }}
        onFinish={handleFormSubmit}
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

        export default CreateForm;
