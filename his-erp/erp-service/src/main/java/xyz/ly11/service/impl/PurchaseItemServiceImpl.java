package xyz.ly11.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.ly11.domain.PurchaseItem;
import xyz.ly11.mapper.PurchaseItemMapper;
import xyz.ly11.service.PurchaseItemService;
/**
 * @author 29794
 * @date 2/1/2021 21:22
 * todo
 */
@Service
public class PurchaseItemServiceImpl extends ServiceImpl<PurchaseItemMapper, PurchaseItem> implements PurchaseItemService{

}
