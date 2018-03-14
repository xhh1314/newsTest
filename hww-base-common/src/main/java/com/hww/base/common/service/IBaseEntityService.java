package com.hww.base.common.service;

import java.io.Serializable;
import java.util.List;

import com.hww.base.common.dto.AbsBaseDto;
import com.hww.base.common.entity.IBaseEntity;
import com.hww.base.common.manager.IBaseMng;

public interface IBaseEntityService<ID extends Serializable, ENTITY extends IBaseEntity<ID>, DTO extends AbsBaseDto<ID>,MNG extends IBaseMng>
{
    /**
     * 保存
     * @param dto
     * @return
     */
    public DTO save(DTO dto);

    /**
     * 更新
     * @param dto
     * @return
     */
    public DTO update(DTO dto);

    /**
     * 保存或更新
     * @param dto
     * @return
     */
    public DTO saveOrUpdate(DTO dto);

    /**
     * 保存
     * @param dto
     * @return
     */
    public DTO merge(DTO dto);

    /**
     * 删除
     * @param dto
     * @return
     */
    public Boolean delete(DTO dto);
    
    /**
     * 查找单个实体
     * @param id
     * @return
     */
    public DTO findById(ID id);
    public DTO findUniqueByProperty(String property, Object value);
    
    /**
     * 查找多个实体
     * @param values
     * @return
     */
    public List<DTO> findByProperty(String property, Object... values);

}
