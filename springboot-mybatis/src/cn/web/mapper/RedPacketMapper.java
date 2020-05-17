package cn.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.web.po.RedPacketPO;
import cn.web.po.RedPacketUserPO;

@Mapper
public interface RedPacketMapper {

	public RedPacketPO getRedPacket(Integer id);
	
	public int updateRedPacketStock(Integer id);
	
	public int insertRedPacketUser(RedPacketUserPO po);
	
	//调用存储过程
	public void grabRedPacketProcedure(Map<String, Integer> map);
	
	public void updateRedPacketUserId(RedPacketUserPO po);
	
	int updateRedPacketStatus(RedPacketPO po);
	
	List<RedPacketPO> getRedPacketByStatus();
}
